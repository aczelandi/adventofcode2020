#!/bin/zsh

bag_color="shiny gold"

input="$(cat input.txt)"

get_bags_that_this_bag_can_fit() {
  input="$1"
  bag_color="$2"
  current_bag_row="$(echo "$input" | grep "^$bag_color")"
  current_bag_contents=$(echo "$current_bag_row" | cut -d " " -f5-)
  current_bag_contents_per_row=$(sed "s/, /\n/g" <<<"$current_bag_contents" | cut -d " " -f-3)

  current_bags_context_empty_excluded=$(echo $current_bag_contents_per_row | grep -v "no other bags")
  echo "$current_bags_context_empty_excluded"
}

get_sum_of_all() {
  bags_to_count="$1"
  bag_multipliers=$(echo "$bags_to_count" | cut -d " " -f-1)

  sum=0
  while read -r line; do
    ((sum += line))
  done <<<"$bag_multipliers"

  echo "$sum"
}

total_sum=0

find_all_bags() {
  bag_color=$1
  local multiplier=$2
  echo "Bag color to check: $bag_color"

  bags_that_this_bag_can_fit=$(get_bags_that_this_bag_can_fit "$input" "$bag_color")
  echo "Bags that this bag can fit: $bags_that_this_bag_can_fit"

  sum_of_bags_in_current=$(get_sum_of_all "$bags_that_this_bag_can_fit")
  echo "Total bags in current $sum_of_bags_in_current"

  echo "Multi $multiplier"
  total_sum=$((total_sum + ($multiplier * $sum_of_bags_in_current)))

  echo "Total $total_sum"

  if [[ -n $bags_that_this_bag_can_fit ]]; then
    while read -r line; do
      echo "Multiplier and color: $line"

      color_multiplier=$(echo "$line" | cut -d " " -f-1)
      color=$(echo "$line" | cut -d " " -f2-3)

      find_all_bags "$color" $(($color_multiplier * $multiplier))
    done <<<"$bags_that_this_bag_can_fit"
  fi
}

find_all_bags "$bag_color" 1

echo "Count of bags that shiny gold can fit: $total_sum"
